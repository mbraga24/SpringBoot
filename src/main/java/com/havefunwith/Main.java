package com.havefunwith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SpringBootApplication
@RestController
public class Main {

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer john = new Customer(1, "John", 24, "john@email.com");
        Customer keyla = new Customer(2, "Keyla", 28, "keila@email.com");

        customers.add(john);
        customers.add(keyla);
    }

    public static void main(String[] args) {
//        System.out.println("Customers DB :: " + customers);
        SpringApplication.run(Main.class, args);
    }

//    @RequestMapping(value = "/api/v1/customers", method = RequestMethod.GET)
    @GetMapping("/api/v1/customers")
    public List<Customer> getCustomers() {
        return customers;
    }

    @GetMapping("/api/v1/customers/{customerId}")
    public Customer getCustomer(@PathVariable Integer customerId) {
        return customers.stream()
                .filter(customer -> customer.id.equals(customerId))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Customer with id [%s] not found.".formatted(customerId)));
    }

    static class Customer {
        private Integer id;
        private String name;
        private Integer age;
        private String email;

        public Customer() {}

        public Customer(Integer id, String name, Integer age, String email) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.email = email;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer customer = (Customer) o;
            return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(age, customer.age) && Objects.equals(email, customer.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, age, email);
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    '}';
        }
    }

}
