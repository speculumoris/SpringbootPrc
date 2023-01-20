package com.tpe.controller;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //1-Spring Bootu selamlama:)->http://localhost:8080/customers/greet
    @GetMapping("/greet")
    public String greetSpringBoot(){
        return "Hello Spring Boot:)";
    }

    //2-customer oluşturma/kaydetme->http://localhost:8080/customers/save + JSON BODY
    @PostMapping("/save")
    public ResponseEntity<String> saveCustomer(@Valid @RequestBody Customer customer){
        customerService.saveCustomer(customer);
        String message="Customer is saved successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    //3-Tüm customerları getirme->http://localhost:8080/customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer(){
        List<Customer> customerList=customerService.getAllCustomer();
        return ResponseEntity.ok(customerList);//200:OK
    }
    //4-Id ile tek bir customer getirme->http://localhost:8080/customers/1
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id){
        Customer customer=customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    //ÖDEV-Id ile tek bir customer getirme->http://localhost:8080/customers/custom?id=1
    @GetMapping("/custom")
    public ResponseEntity<CustomerDTO> getCustomerDTOById(@RequestParam("id") Long id) {
        CustomerDTO customerDto = customerService.getCustomerDtoById(id);
        return ResponseEntity.ok(customerDto);
    }

    //ÖDEV2-Id ile bir customerı silme->http://localhost:8080/customers/custom?id=1
    //Customer is deleted successfully mesajı dönsün.

    @DeleteMapping("/custom")
    public ResponseEntity<String> deleteCustomerById(@RequestParam("id") Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer is deleted successfully");//200
    }

    //5-id ile customer ı update etme->http://localhost:8080/customers/update/1
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomerById(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomerById(id, customerDTO);
        return ResponseEntity.ok("Customer is updated successfully");//200
    }
    //pagination?
    //6-tüm customerları page page gösterme
    //http://localhost:8080/customers/page?page=1&size=2&sort=id&direction=ASC
    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> getAllCustomerByPage(@RequestParam("page") int page,//hangi sayfa
                                                               @RequestParam("size") int size,//her sayfada kaç adet
                                                               @RequestParam("sort") String prop,//sıralama fieldı
                                                               @RequestParam("direction") Sort.Direction direction){//ASC,DESC
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Customer> customersPage=customerService.getAllCustomerByPage(pageable);
        return ResponseEntity.ok(customersPage);
    }












}
