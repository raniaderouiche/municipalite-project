package org.fsb.municipalite.entities;

import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Compte extends BaseEntity{
    private String username;
    private String password;
    private String question;
	private String answer;
    private int loginSessions;
    
    @OneToOne
    @JoinColumn
    private Employee employee;

    public Compte() {
    	this.question = "";
    	this.answer = "";
    }

    public Compte(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getLoginSessions() {
		return loginSessions;
	}

	public void setLoginSessions(int loginSessions) {
		this.loginSessions = loginSessions;
	}
	
	public Long getEmpID() {
		return employee.getId();
	}
	
	public String getCreatedAtValue(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        return createdAt.format(format);
    }
    @Override
    public String toString() {
        return "Compte{" +
                "createdAt=" + createdAt +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", employee=" + employee + '\'' +
                ", question=" + question + '\'' +
                ", answer=" + answer + + '\'' +
                ", loginSessions = " + loginSessions + '\'' +
                '}';
    }
}
