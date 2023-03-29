package com.zahid;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.javafaker.Faker;
import com.zahid.accesscard.AccessCard;
import com.zahid.employee.Employee;
import com.zahid.employee.EmployeeRepository;
import com.zahid.employee.EmployeeType;

@SuppressWarnings("all")
@SpringBootApplication
@EnableTransactionManagement
public class Application {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	private EntityManager entityManager; // shared instance among multiple threads, "Not allowed to create transaction on shared EntityManager"
	// this share entity manager can be used to read bu not for write transaction

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager2; // not shared instance 

	@Autowired
	private EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		System.out.println("# Welcome to Spring Boot Application");
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void start() {
		createEntity();
		// createEntity2();
		// createEntity3();
		updateEntity();
	}

	// @Transactional
	// @Transactional(value = Transactional.TxType.REQUIRED)
	// @Transactional(value = Transactional.TxType.REQUIRES_NEW)
	// @Transactional(value = Transactional.TxType.NOT_SUPPORTED)
	@Transactional(value = Transactional.TxType.MANDATORY)
	// @Transactional(rollbackOn = SQLException.class)
	private void updateEntity() {

		Employee employee = employeeRepository.findById(1).get();
		System.out.println(employee);
		
		System.out.println(employeeRepository.count());
		
		employee.setName("Alex Lee");
		employee.setAge(30);
		employee.setSsn("123-456-7890");
		employee.setType(EmployeeType.FULL_TIME);
		
		employeeRepository.save(employee);

		System.out.println(employee);
	}

	private void createEntity3() {

		Employee employee = employeeRepository.findById(1).get();
		System.out.println(employee);
		
		System.out.println(employeeRepository.count());

		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		System.out.println(employees.size());
		employees.forEach(System.out::println);

		Employee employee2 = new Employee();
		employee2.setName("Alex Lee");
		employee2.setAge(30);
		employee2.setSsn("123-456-7890");

		employeeRepository.save(employee2);
	}

	// using the EntityManager
	public void createEntity2() {
        Faker faker = new Faker();
        Random random = new Random();

		// # Reading with the shared EntityManager
        Employee employee1 = entityManager.find(Employee.class, 1); 
		System.out.println(employee1);

		/* EntityTransaction transaction = entityManager2.getTransaction();
		// # writing with the non-shared EntityManager
   
		Employee employee = new Employee();
		employee.setName(faker.name().fullName());
		employee.setAge(random.nextInt(31) + 18);
		employee.setDob(new Date(random.nextInt(20) + 80, 0, 1)); // starts from 1900
		employee.setType(EmployeeType.values()[random.nextInt(EmployeeType.values().length)]);
		employee.setSsn(faker.numerify("###-###-####"));

		transaction.begin();
		entityManager2.persist(employee);
		transaction.commit(); */
    }

	// using the EntityManagerFactory 
	public void createEntity() {
        Faker faker = new Faker();
        Random random = new Random();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        for (int i = 0; i < 10; i++) {
            AccessCard accessCard = new AccessCard();
            accessCard.setIssueDate(new Date());
            accessCard.setIsActive(random.nextBoolean());
            accessCard.setFirmwareVersion("1.0");

            Employee employee = new Employee();
            // employee.setId(i+1);
            employee.setName(faker.name().fullName());
            employee.setAge(random.nextInt(31) + 18);
            employee.setDob(new Date(random.nextInt(20) + 80, 0, 1)); // starts from 1900
            employee.setType(EmployeeType.values()[random.nextInt(EmployeeType.values().length)]);
            employee.setSsn(faker.numerify("###-###-####"));
            // employee.setSsn(faker.numerify("##########"));

            employee.setAccessCard(accessCard);
            accessCard.setEmployee(employee);

            transaction.begin();
            // entityManager.persist(accessCard);
            entityManager.persist(employee); // employee is thr owner of the relationship, so saving employee saves
                                             // access_card too
            transaction.commit();
        }

        entityManager.close();
    }
}
