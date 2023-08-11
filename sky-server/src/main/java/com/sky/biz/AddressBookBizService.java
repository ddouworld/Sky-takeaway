package com.sky.biz;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/12 15:59:55
 * @Description 地址管理
 */
public interface AddressBookBizService {
    /**
     * 地址管理 - 新增
     *
     * @param addressBook
     * @return
     */
    Boolean saveAddressBook(AddressBook addressBook);

    /**
     * C端-查询地址列表
     *
     * @return
     */
    List<AddressBook> listByUserId(Long userId);


    /**
     * C端-查询地址列表
     *
     * @return
     */
    AddressBook defaultAddressBookByUserId(Long userId);

    /**
     * C端-根据ID修改地址
     *
     * @return
     */
    Boolean updateAddressBook(AddressBook updateAddressBook);

    /**
     * C端-根据ID删除地址
     *
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * C端-根据id查询地址
     *
     * @return
     */
    AddressBook findById(Long id);

    /**
     * C端-设置默认地址
     *
     * @return
     */
    Boolean setDefault(Integer id);
}
