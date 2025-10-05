package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryContoller {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    @ApiOperation("按照分类查菜品")
    public Result<List<Category>> find(Integer type){
        List<Category> categoryList = categoryService.getCategoryList(type);
        return Result.success(categoryList);
    }
}
