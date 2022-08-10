package io.javafun.springsecurityjwt;

import io.javafun.springsecurityjwt.models.ResponseDT;
import io.javafun.springsecurityjwt.models.BankUser;
import io.javafun.springsecurityjwt.security.ExecutorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
public class HelloResource {

    @Autowired
    private ExecutorServiceImpl executorServiceimpl;

 @RequestMapping("/hello")
  public String hello(){
      return "Hello World";
  }



  @PostMapping(value="/checkExecutor", consumes=MediaType.APPLICATION_JSON_VALUE , produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDT> checkExecutor(@RequestBody BankUser newBankUser) throws ExecutionException, InterruptedException {

      Optional<BankUser> bankUser=(Optional) executorServiceimpl.checkExecutorFramework(newBankUser);

      return new ResponseEntity<ResponseDT>(HttpStatus.CREATED);
  }

    @PutMapping(value="/updateBalance", consumes=MediaType.APPLICATION_JSON_VALUE , produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDT> updateBalance(@RequestBody BankUser newBankUser) throws ExecutionException, InterruptedException {
        System.out.println("Inside the PUT Method of helloController class");
        Optional<BankUser> bankUser=(Optional) executorServiceimpl.checkExecutorFramework(newBankUser);

        return new ResponseEntity<ResponseDT>(HttpStatus.CREATED);
    }



}
