package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    AddressBook getAddressBookById(AddressBook addressBook);

    List<AddressBook> getDefaultAddressBook( AddressBook addressBook);

    List<AddressBook> getAllAddressBook(AddressBook addressBook);

    void setDefaultAddressBook(Long id);

    void updateAddressBook(AddressBook addressBook);

    void insertAddressBook(AddressBook addressBook);

    void deleteAddressBook(Long id);
}
