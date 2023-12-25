package com.metrodata.serverapp.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.metrodata.serverapp.entity.Person;
import com.metrodata.serverapp.exception.CustomException;
import com.metrodata.serverapp.model.request.PersonRequest;
import com.metrodata.serverapp.model.response.AccountResponse;
import com.metrodata.serverapp.model.response.PersonResponse;
import com.metrodata.serverapp.repository.AccountRepository;
import com.metrodata.serverapp.repository.PersonRepository;
import com.metrodata.serverapp.repository.VerificationTokenRepository;
import com.metrodata.serverapp.service.PersonService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private AccountRepository accountRepository;
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<PersonResponse> getAll() {
        return personRepository.findAll()
                .stream()
                .map(person -> {
                    return mappingPersonToPersonResponse(person);
                }).collect(Collectors.toList());
    }

    @Override
    public PersonResponse getById(long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Person with given id : " + id + " is not found",
                        "PERSON_NOT_FOUND",
                        404
                ));
        return mappingPersonToPersonResponse(person);
    }

    @Override
    public List<PersonResponse> getByVisibility(){
        return personRepository.getByVisibility().stream()
                .map(person -> {
                    return mappingPersonToPersonResponse(person);
                }).collect(Collectors.toList());
    }

    @Override
    public PersonResponse create(PersonRequest personRequest) {
        Person person = new Person();
        BeanUtils.copyProperties(personRequest, person);
        person.setVisible(false);
        person.setEmployee(false);

        Person res = personRepository.save(person);
        return mappingPersonToPersonResponse(res);
    }

    @Override
    public PersonResponse update(long id, PersonRequest personRequest) {
        PersonResponse personRes = getById(id); // old data


        Person person = new Person();

        /*person.setName(personRes.getName());
        person.setEmail(personRes.getEmail());
        person.setAddress(personRes.getAddress());
        person.setPhoneNumber(personRes.getPhoneNumber());

        person.setName(personRequest.getName());
        person.setEmail(personRequest.getEmail());
        person.setAddress(personRequest.getAddress());
        person.setPhoneNumber(personRequest.getPhoneNumber());*/
        BeanUtils.copyProperties(personRes,person); // Copy properties from old data

        // Update with new data
        BeanUtils.copyProperties(personRequest,person);

        Person res = personRepository.save(person);
        return mappingPersonToPersonResponse(res);
    }

    @Override
    public PersonResponse delete(long id) {
        PersonResponse personResponse = getById(id);
        verificationTokenRepository.deleteByAccountId(id);
        accountRepository.deleteById(id);
        personRepository.deleteById(id);
        accountRepository.deleteById(id);
        return personResponse;
    }

    private PersonResponse mappingPersonToPersonResponse(Person person){
        PersonResponse personResponse = new PersonResponse();
        BeanUtils.copyProperties(person, personResponse);

        AccountResponse accountResponse = new AccountResponse();
        if(person.getAccount() != null){
            BeanUtils.copyProperties(person.getAccount(), accountResponse);
            personResponse.setAccount(accountResponse);
        }
        
        
        return personResponse;
    }
}