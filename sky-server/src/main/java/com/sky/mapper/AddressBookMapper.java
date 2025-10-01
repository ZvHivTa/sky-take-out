package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    List<AddressBook> getAddressBook(AddressBook addressBook);

    @Update("update sky_take_out.address_book set is_default = 1 where id = #{id}")
    void setDefaultAddressBook(Long id);

    void updateAddressBook(AddressBook addressBook);

    void insertAddressBook(AddressBook addressBook);

    @Delete("delete from sky_take_out.address_book where id = #{id}")
    void deleteAddressBook(Long id);

    @Update("update sky_take_out.address_book set is_default = #{isDefault} where user_id = #{userId}")
    void updateIsDefaultByUserId(AddressBook addressBook);
}
