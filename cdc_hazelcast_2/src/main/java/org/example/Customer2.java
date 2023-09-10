package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Customer2 implements Serializable {

    @JsonProperty("id")
    public int id;

    @JsonProperty("first_name")
    public String first_name;

    @JsonProperty("last_name")
    public String last_name;

    @JsonProperty("email")
    public String email;

    public Customer2() {
    }

    public Customer2(int id, String first_name, String last_name, String email) {
        super();
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(email, first_name, id, last_name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Customer2 other = (Customer2) obj;
        return id == other.id
                && Objects.equals(first_name, other.first_name)
                && Objects.equals(last_name, other.last_name)
                && Objects.equals(email, other.email);
    }

    @Override
    public String toString() {
        return "Customer {id=" + id + ", firstName=" + first_name + ", lastName=" + last_name + ", email=" + email + '}';
    }
}