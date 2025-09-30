package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/dish")
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/list")
    @ApiOperation("根据分类id查菜品")
    public Result<List<DishVO>> findDishById(Long categoryId){
        //构造redis中的key 使用: dish_(categoryId)
        String key = "dish_" + categoryId;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //先从redis查
        List<DishVO> dishVO = (List<DishVO>) valueOperations.get(key);
        if(dishVO != null && dishVO.size() > 0){
            return Result.success(dishVO);
        }
        //不存在的情况才查数据库
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        dishVO = dishService.getDishWithFlavors(dish);

        //查出来后把数据放到redis里
        valueOperations.set(key,dishVO);

        return Result.success(dishVO);
    }
}
