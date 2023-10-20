package com.havefunwith.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
    Data Access Layer or DAO (Data Access Object) Layer
 */
@Slf4j
@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age 
                FROM customer;
                """;
        log.info("selectAllCustomers ::");
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        var sql = """
                SELECT id, name, email, age 
                FROM customer
                WHERE id = ?
                """;
        log.info("selectCustomerById ::");
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        log.info("insertCustomer :: " + result + " ROW ADDED");
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("CustomerJDBCDataAccessService :: deleteCustomer ====> " + id);
        var sql = """
                DELETE 
                FROM customer
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, id);
        log.info("deleteCustomer :: " + result + " ROW DELETED");
    }

    @Override
    public void updateCustomer(Customer customer) {
        var sql = "";

        if (customer.getEmail() != null) {
            sql = """
                    UPDATE customer
                    SET email = ?
                    WHERE id = ? 
                    """;
            jdbcTemplate.update(
                    sql,
                    customer.getEmail(),
                    customer.getId());
            log.info("updateCustomer :: " +  customer.getEmail() + " UPDATED");
        }

        if (customer.getName() != null) {
            sql = """
                    UPDATE customer 
                    SET name = ?
                    WHERE id = ?
                    """;
            jdbcTemplate.update(
                    sql,
                    customer.getName(),
                    customer.getId());
            log.info("updateCustomer :: " +  customer.getName() + " UPDATED");
        }

        if (customer.getAge() != null) {
            sql = """
                    UPDATE customer
                    SET age = ?
                    WHERE id = ?
                    """;
            jdbcTemplate.update(
                    sql,
                    customer.getAge(),
                    customer.getId());
            log.info("updateCustomer :: " +  customer.getAge() + " UPDATED");
        }
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT COUNT(email)
                FROM customer
                WHERE name = ?
                """;
        Long count = jdbcTemplate.queryForObject(sql, Long.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsPersonById(Long id) {
        var sql = """
                SELECT COUNT(id)
                FROM customer
                WHERE id = ?
                """;
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count != null && count > 0;
    }
}
