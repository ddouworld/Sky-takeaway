package com.sky.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookDao;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地址簿 服务实现类
 * </p>
 *
 * @author 尹志伟
 * @since 2023-06-30
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {

    @Autowired
    private AddressBookDao addressBookDao;

    /**
     * 地址 - 查询用户下有几个地址
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public Integer countByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AddressBook::getUserId, userId);
            return count(queryWrapper);
        }
        return 0;
    }

    /**
     * 地址 -查询地址列表
     *
     * @return
     */
    @Override
    public List<AddressBook> listByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AddressBook::getUserId, userId);
            return list(queryWrapper);
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
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getIsDefault, Boolean.TRUE);
            return getOne(queryWrapper);
        }
        return null;
    }

    /**
     * 根据Id 修改用户默认地址为不是默认的
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public Boolean updateDefaultToUnDefaultByUserId(Long userId) {
        if (ObjectUtil.isNotNull(userId)) {
            LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AddressBook::getUserId, userId).eq(AddressBook::getIsDefault, Boolean.TRUE);
            AddressBook addressBook = getOne(queryWrapper);
            if (ObjectUtil.isNotNull(addressBook)) {
                addressBook.setIsDefault(Boolean.FALSE);
                return updateById(addressBook);
            }
        }
        return Boolean.FALSE;
    }
}
