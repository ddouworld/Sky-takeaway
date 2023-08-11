package com.sky.biz.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sky.biz.AddressBookBizService;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/12 16:01:25
 * @Description 地址管理
 */
@Service
@Slf4j
public class AddressBookBizServiceImpl implements AddressBookBizService {

    @Autowired
    private AddressBookService addressBookService;


    /**
     * 地址管理 - 新增
     *
     * @param addressBook
     * @return
     */
    @Override
    public Boolean saveAddressBook(AddressBook addressBook) {
        if (ObjectUtil.isNotNull(addressBook)) {
            //查询一下该读者是否有默认地址，如果没有则第一个赋予默认地址
            Integer addressBookCount = addressBookService.countByUserId(addressBook.getUserId());
            if (addressBookCount <= 0) {
                addressBook.setIsDefault(Boolean.TRUE);
            }
            return addressBookService.save(addressBook);
        }
        return Boolean.FALSE;
    }

    /**
     * C端-查询地址列表
     *
     * @return
     */
    @Override
    public List<AddressBook> listByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            return addressBookService.listByUserId(userId);
        }
        return null;
    }

    /**
     * C端-查询地址列表
     *
     * @return
     */
    @Override
    public AddressBook defaultAddressBookByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            return addressBookService.defaultAddressBookByUserId(userId);
        }
        return null;
    }

    /**
     * C端-根据ID修改地址
     *
     * @return
     */
    @Override
    public Boolean updateAddressBook(AddressBook updateAddressBook) {
        if (ObjectUtil.isNotNull(updateAddressBook)) {
            Long id = updateAddressBook.getId();
            if (ObjectUtil.isNotNull(id)) {
                return addressBookService.updateById(updateAddressBook);
            }
        }
        return null;
    }

    /**
     * C端-根据ID删除地址
     *
     * @return
     */
    @Override
    public Boolean deleteById(Long id) {
        if (ObjectUtil.isNotNull(id)) {
            return addressBookService.removeById(id);
        }
        return Boolean.FALSE;
    }

    /**
     * C端-根据id查询地址
     *
     * @return
     */
    @Override
    public AddressBook findById(Long id) {
        if (ObjectUtil.isNotNull(id)) {
            return addressBookService.getById(id);
        }
        return null;
    }

    /**
     * C端-设置默认地址
     *
     * @return
     */
    @Override
    public Boolean setDefault(Integer id) {
        if (ObjectUtil.isNotNull(id)) {
            //先修改默认地址为不是默认的，默认地址只能有一个
            Long userId = BaseContext.getCurrentId();
            addressBookService.updateDefaultToUnDefaultByUserId(userId);
            //查询出来进行更改
            AddressBook addressBook = addressBookService.getById(id);
            if (ObjectUtil.isNotNull(addressBook)) {
                addressBook.setIsDefault(true);
                return addressBookService.updateById(addressBook);
            }
        }
        return Boolean.FALSE;
    }
}
