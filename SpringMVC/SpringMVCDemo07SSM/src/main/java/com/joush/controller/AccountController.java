package com.joush.controller;

import com.joush.domain.Account;
import com.joush.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 账户 web
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/findAll")
    public String findAll(Model model){
        System.out.println("表现层：findAll executed!");
        List<Account> list = accountService.findAll();
        model.addAttribute("list", list);
        return "list";
    }

    /**
     * 保存方法
     * @param account
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/save")
    public void save(Account account, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 保存
        accountService.saveAccount(account);

        // 直接执行查询所有，查看是否保存成功
        response.sendRedirect(request.getContextPath() + "/account/findAll");

    }

}
