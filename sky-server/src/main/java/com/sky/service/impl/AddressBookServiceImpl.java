package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public AddressBook getAddressBookById(AddressBook addressBook) {
        List<AddressBook> addressBook1 = addressBookMapper.getAddressBook(addressBook);
        return addressBook1.get(0);
    }

    @Override
    public List<AddressBook> getDefaultAddressBook(AddressBook addressBook) {
        return addressBookMapper.getAddressBook(addressBook);
    }

    @Override
    public List<AddressBook> getAllAddressBook(AddressBook addressBook) {
        return addressBookMapper.getAddressBook(addressBook);
    }

    @Override
    @Transactional
    public void setDefaultAddressBook(Long id) {
        //1、将当前用户的所有地址修改为非默认地址 update address_book set is_default = ? where user_id = ?
        AddressBook addressBook = AddressBook.builder()
                .isDefault(0)
                .userId(BaseContext.getCurrentId())
                .build();

        addressBookMapper.updateIsDefaultByUserId(addressBook);

        //2、将当前地址改为默认地址 update address_book set is_default = ? where id = ?
        addressBookMapper.setDefaultAddressBook(id);
    }

    @Override
    public void updateAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateAddressBook(addressBook);
    }

    @Override
    public void insertAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.insertAddressBook(addressBook);
    }

    @Override
    public void deleteAddressBook(Long id) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(id);
        List<AddressBook> addressBook1 = addressBookMapper.getAddressBook(addressBook);
        if(addressBook1.isEmpty() || addressBook1 == null){
            throw new DeletionNotAllowedException("该地址不存在");
        }
        addressBookMapper.deleteAddressBook(id);
    }
}
