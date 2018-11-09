package edu.cnm.deepdive.qod.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.qod.view.Flat;
import edu.cnm.deepdive.qod.view.Nested;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@JsonView(Flat.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Quote {

  private static EntityLinks entityLinks;

  @PostConstruct
  private void init(){
    String ignore = entityLinks.toString();//reference entity object in a way that requires it to be nonnull
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks){
    Quote.entityLinks = entityLinks;
  }

  @Id//this means this is the key value
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name="uuid2", strategy = "uuid2")
  @Column(name = "quote_id", columnDefinition = "CHAR(16) FOR BIT DATA", nullable = false, updatable = false)
  private UUID id;

  @NonNull//this means hibernate will prevent a null from getting passed in
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @Column(length = 4096, nullable = false)
  private String text;

  @NonNull
  @JsonView(Nested.class)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "source_id", nullable = false, updatable = false)//source_id here refers to the table called source_id, it looksup the primary key in that table
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Source source;

  public UUID getId() {
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

  public URI getHref() {
    return entityLinks.linkFor(Quote.class, source.getId())
        .slash(id)
        .toUri();
  }

  public Source getSource() {
    return source;
  }

  public void setSource(Source source) {
    this.source = source;
  }
}
