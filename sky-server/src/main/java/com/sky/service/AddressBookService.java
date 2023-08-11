package com.sky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.entity.AddressBook;

import java.util.List;


/**
 * <p>
 * 地址簿 服务类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
public interface AddressBookService extends IService<AddressBook> {

    /**
     * 地址 - 查询用户下有几个地址
     *
     * @param userId 用户ID
     * @return
     */
    Integer countByUserId(Long userId);

    /**
     * 地址 -查询地址列表
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
     * 根据Id 修改用户默认地址为不是默认的
     *
     * @param userId 用户ID
     * @return
     */
    Boolean updateDefaultToUnDefaultByUserId(Long userId);
}
