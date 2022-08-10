package io.javafun.springsecurityjwt.security;

import io.javafun.springsecurityjwt.Repositories.UserRepository;
import io.javafun.springsecurityjwt.models.BankUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

@Service
public class ExecutorServiceImpl {

    @Autowired
    private UserRepository userRepository;

    ExecutorService executorService = Executors.newCachedThreadPool();

    private static ThreadLocal<Integer> transactionId=ThreadLocal.withInitial(()-> 1);


    public Object checkExecutorFramework(BankUser users) throws ExecutionException, InterruptedException {

        int coreCount = Runtime.getRuntime().availableProcessors();

        System.out.println(" ThreadPool created with this request "+executorService.hashCode());
        System.out.println(" name of the current Thread ---> "+Thread.currentThread().getName());
        final BankUser bankUserOP;
        Future future = executorService.submit(new Callable() {
            @Override
            public Object call() throws Exception {


                if (users.getBalance().equals(-1)) {
                    users.setBalance(0);
                } else {
                    users.setBalance(users.getBalance() + 100);
                }
                users.setFirstName(users.getFirstName());
                users.setLastName(users.getLastName());
                //Thread.sleep(30000);
                Long transaId= Instant.now().toEpochMilli();
                transactionId.set(transaId.intValue());
                users.setTransactionId(transactionId.get());
                Optional<BankUser>  bankUserFinal=Optional.empty();

                if(Optional.ofNullable(users.getUserId()).isPresent()){
                    System.out.println("-------> In the else block to update the same user -------------->");
                      bankUserFinal =userRepository.findById(users.getUserId());
                      BankUser bankUser=bankUserFinal.get();
                      users.setBalance(bankUser.getBalance()+users.getBalance());
                }
                bankUserFinal = Optional.ofNullable(userRepository.save(users));
                 HashMap<Integer,String> hm=new HashMap<>();

                //transactionId=transactionId+2;
                System.out.println(" Value of Transaction id for Thread name -- "+Thread.currentThread().getName()+" value --> "+transactionId.get());
                return bankUserFinal;
            }

        });

        return future.get();
    }
}
