package com.metrodata.serverapp.service;

import java.util.List;

import com.metrodata.serverapp.model.request.AccountRequest;
import com.metrodata.serverapp.model.response.AccountResponse;

public interface AccountService {
    //Fungsi Mengumpulkan semua data Account
    List<AccountResponse> getAll();

    //Fungsi Account mencari berdasarkan id(long id)
    AccountResponse getById(long id);

    AccountResponse getByUsername(String username);

    //Fungsi update akun
    AccountResponse update(long id, AccountRequest accountRequest);

    //Fungsi delete akun
    AccountResponse delete(long id);
}