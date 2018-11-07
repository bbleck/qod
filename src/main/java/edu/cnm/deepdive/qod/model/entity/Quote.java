package edu.cnm.deepdive.qod.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Quote {

  @Id//this means this is the key value
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "quote_id", nullable = false, updatable = false)
  private long id;

  @NonNull//this means hibernate will prevent a null from getting passed in
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @Column(length = 4096, nullable = false)
  private String text;

  @NonNull
  @Column(length = 1024, nullable = false)
  private String source;

  public long getId() {
    return id;
  }

  public Date getCreated() {
    return created;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}