package com.metrodata.serverapp.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Account;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.request.AccountRequest;
import com.metrodata.serverapp.model.response.AccountResponse;
import com.metrodata.serverapp.repository.AccountRepository;
import com.metrodata.serverapp.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    //private RoleRepository roleRepository;
//    private PersonRepository personRepository;
//    private PasswordEncoder passwordEncoder;


    @Override
    public List<AccountResponse> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(account -> {
                    return mappingAccountToAccountResponse(account);
                }).collect(Collectors.toList());
    }

    @Override
    public AccountResponse getById(long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Account with given id : " + id + " is not found",
                        "ACCOUNT_NOT_FOUND",
                        404
                ));
        return mappingAccountToAccountResponse(account);
    }

     @Override
     public AccountResponse getByUsername(String username) {
         Account account = accountRepository.findByUsernameOrPerson_Email(username, username)
                 .orElseThrow(() -> new CustomException(
                         "Account with given username : " + username + " is not found",
                         "ACCOUNT_NOT_FOUND",
                         404
                 ));
         return mappingAccountToAccountResponse(account);
     }

    @Override
    public AccountResponse update(long id, AccountRequest accountRequest) {
        AccountResponse accountRes = getById(id);
        Account account = new Account();

        BeanUtils.copyProperties(accountRes, account); //old data
        BeanUtils.copyProperties(accountRequest, account); // new data

        Account res = accountRepository.save(account);
        return mappingAccountToAccountResponse(res);
    }

    @Override
    public AccountResponse delete(long id) {
        AccountResponse accountResponse = getById(id);
        accountRepository.deleteById(id);
        return accountResponse;
    }

    private AccountResponse mappingAccountToAccountResponse(Account account) {
        //Mapping Account -> AccountResponse
        AccountResponse accountResponse = new AccountResponse();
        BeanUtils.copyProperties(account, accountResponse);

        return accountResponse;
    }

}