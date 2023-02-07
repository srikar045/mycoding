package com.ex.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ex.models.Employee;
import com.ex.models.Roles;
import com.ex.repository.EmpRepo;
import com.ex.repository.RoleRepo;

@Service
public class ServiceEmpImpl implements EmpService {
	private Date date = new Date();

	@Autowired
	EmpRepo empRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSenderObj;

	public boolean saveEmp(Employee emp) {
		String pass=emp.getPassword();
		Employee empusername = empRepo.findByUsername(emp.getUsername());
		Optional<Employee> empmail = empRepo.findByEmail(emp.getEmail());
		if (empmail.isPresent() || empusername != null) {
			return false;
		} else {
			Roles role = roleRepo.findByRole("Employee");
			Set<Roles> userRole = new HashSet<>();
			userRole.add(role);

			Employee Emp = emp;
			Emp.setPassword(passwordEncoder.encode(emp.getPassword()));

			Emp.setRole(userRole);

			Emp.setCreated_by("SUPER_ADMIN");
			Emp.setCreated_on(date);
			Emp.setUpdated_by(null);
			Emp.setUpdated_on(null);
			Employee eee=empRepo.save(Emp);

			sendmail(emp,eee.getEid(),pass);
			return true;
		}

	}
    private void sendmail(Employee employee,int id,String pass) {
        final String emailToRecipient = employee.getEmail();
        final String emailSubject = "Registration Successfull";
        final String emailMessage1 = "<html> <body> <p>You have been Successfully Registred" + "<br><br>"
                + " <h2>Registration details</h2>"
                + "<table border='1' width='300px' style='text-align:center;font-size:20px;'>"
                + "<tr><td>User ID</td><td>" + id + "</td></tr>"
                + "<tr><td>User Name</td><td>" + employee.getUsername() + "</td></tr><tr><td>Password</td><td>"
                + pass + "</td></tr><tr><td>Gender</td><td>" + employee.getGender()
                + "</td></tr><tr><td>Mobile</td><td>" + employee.getMobile() + "</table>"
                + "<p>Login to Your Account Using your Username and Password</p>" + "<br>" + "<p>Thanks and regards</p>"
                + "</p>" + "<p>Stratapps Solutions Pvt Ltd</body></html>";
        mailSenderObj.send(new MimeMessagePreparator() {
			
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                mimeMsgHelperObj.setTo(emailToRecipient);
                mimeMsgHelperObj.setText(emailMessage1, true);

                mimeMsgHelperObj.setSubject(emailSubject);

            }
        });

    }
	public List<Employee> getAll() {

		return empRepo.findAll();
	}

	public Optional<Employee> findEmp(int number) {
		Optional<Employee> emp = empRepo.findById(number);
		return emp;
	}

	public void delete(int id) {
		empRepo.deleteById(id);

	}

	public Employee update(int id, Employee Emp) {
		Optional<Employee> data = empRepo.findById(id);
		Employee empusername = empRepo.findByUsername(Emp.getUsername());
		Optional<Employee> empmail = empRepo.findByEmail(Emp.getEmail());
		if (data.isPresent()) {
//			if (empmail.isPresent() || empusername != null) {
//				return null;
//			} else {
			Employee emp = data.get();
			emp.setFname(Emp.getFname());
			emp.setLname(Emp.getLname());
			emp.setGender(Emp.getGender());
			emp.setEmail(Emp.getEmail());
			emp.setMobile(Emp.getMobile());
			emp.setUsername(Emp.getUsername());
			emp.setPassword((emp.getPassword()));
			emp.setRole(Emp.getRole());
			emp.setUpdated_by("SUPER_ADMIN");
			emp.setUpdated_on(date);

			return empRepo.save(emp);}
//		}
			else {
			return null;
		}
	}

	@Override
	public boolean getEmail(String email) {
		Optional<Employee> empEmail = empRepo.findByEmail(email);
		if (empEmail.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean getUsername(String username) {
		Employee empUsername = empRepo.findByUsername(username);
		if (empUsername != null) {
			return true;
		} else {
			return false;
		}
	}
	// we can use this method or we can encode directly in the code
//	public String getEncoded (String password) {
//		return passwordEncoder.encode((password));
//	}
}
