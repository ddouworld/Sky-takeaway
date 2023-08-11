package com.sky.controller.user;

import com.sky.base.BaseController;
import com.sky.biz.AddressBookBizService;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 尹志伟
 * @date 2023/7/4 00:41:20
 * @Description C端 - 地址管理
 */
@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "C端 - 地址管理")
public class WeChatAddressBookController extends BaseController {

    @Autowired
    private AddressBookBizService addressBookBizService;

    /**
     * 地址管理 - 新增
     *
     * @param addressBook
     * @return
     */
    @ApiOperation("C端-新增地址")
    @PostMapping
    public Result<Boolean> saveAddressBook(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("weChat saveAddressBook：{}, userID:{}", addressBook.toString(), addressBook.getUserId());
        return addressBookBizService.saveAddressBook(addressBook) ?
                successReturnBoolean() : errorReturnBoolean();
    }

    /**
     * C端-查询地址列表
     *
     * @return
     */
    @ApiOperation("C端-查询地址列表")
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        return Result.success(addressBookBizService.listByUserId(BaseContext.getCurrentId()));
    }

    /**
     * C端-查询地址列表
     *
     * @return
     */
    @ApiOperation("C端-查询默认地址")
    @GetMapping("/defaultAddress")
    public Result<AddressBook> defaultAddress() {
        return Result.success(addressBookBizService.defaultAddressBookByUserId(BaseContext.getCurrentId()));
    }


    /**
     * C端-根据ID修改地址
     *
     * @return
     */
    @ApiOperation("C端-修改地址")
    @PutMapping
    public Result<Boolean> updateAddressBook(@RequestBody AddressBook updateAddressBook) {
        updateAddressBook.setUserId(BaseContext.getCurrentId());
        log.info("weChat updateAddressBook：{}, userID:{}", updateAddressBook.toString(), updateAddressBook.getUserId());
        return addressBookBizService.updateAddressBook(updateAddressBook) ?
                successReturnBoolean() : errorReturnBoolean();
    }


    /**
     * C端-根据ID删除地址
     *
     * @return
     */
    @ApiOperation("C端-删除地址")
    @DeleteMapping
    public Result<Boolean> deleteAddressBookById(Long id) {
        log.info("weChat deleteAddressBookById：{}", id);
        return Result.success(addressBookBizService.deleteById(id));
    }


    /**
     * C端-根据id查询地址
     *
     * @return
     */
    @ApiOperation("C端-根据id查询地址")
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable("id") Long id) {
        return Result.success(addressBookBizService.findById(id));
    }


    /**
     * C端-设置默认地址
     *
     * @return
     */
    @ApiOperation("C端-设置默认地址")
    @PutMapping("/default")
    public Result<Boolean> setDefault(Integer id) {
        return addressBookBizService.setDefault(id) ?
                successReturnBoolean() : errorReturnBoolean();
    }

}
