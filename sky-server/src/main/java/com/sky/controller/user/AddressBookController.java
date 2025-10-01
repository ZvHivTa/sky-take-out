package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址簿相关")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getAddressBookById(@PathVariable Long id) {
        AddressBook addressBook = AddressBook.builder()
                .id(id)
                .userId(BaseContext.getCurrentId())
                .build();

        AddressBook result = addressBookService.getAddressBookById(addressBook);
        return Result.success(result);
    }

    /**
     * 查询默认地址
     * @return
     */
    @ApiOperation("查询默认地址")
    @GetMapping("/default")
    public Result<List<AddressBook>> getDefaultAddressBook(){
        AddressBook addressBook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .isDefault(1)
                .build();
        List<AddressBook> results = addressBookService.getDefaultAddressBook(addressBook);
        if(results == null || results.isEmpty()){
            return Result.error("没有查询到默认地址");
        }
        return Result.success(results);
    }

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    @ApiOperation("查询当前登录用户的所有地址信息")
    @GetMapping("/list")
    public Result<List<AddressBook>> getAllAddressBook(){
        AddressBook addressBook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        List<AddressBook> results = addressBookService.getAllAddressBook(addressBook);
        if(results == null || results.isEmpty()){
            return Result.error("没有查询到地址");
        }
        return Result.success(results);
    }

    /**
     * 设置默认地址
     * @param id
     * @return
     */
    @ApiOperation("设置默认地址")
    @PutMapping("/default")
    public Result setDefaultAddressBook(Long id){
        addressBookService.setDefaultAddressBook(id);
        return Result.success();
    }

    /**
     * 根据id修改地址
     * @param addressBook
     * @return
     */
    @ApiOperation("根据id修改地址")
    @PutMapping
    public Result updateAddressBook(@RequestBody AddressBook addressBook){
        addressBookService.updateAddressBook(addressBook);
        return Result.success();
    }

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @ApiOperation("新增地址")
    @PostMapping
    public Result insertAddressBook(@RequestBody AddressBook addressBook){
        addressBookService.insertAddressBook(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除地址
     * @param id
     * @return
     */
    @ApiOperation("根据id删除地址")
    @DeleteMapping
    public Result deleteAddressBook(Long id){
        addressBookService.deleteAddressBook(id);
        return Result.success();
    }
}
