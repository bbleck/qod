package edu.cnm.deepdive.qod.controller;


import edu.cnm.deepdive.qod.model.dao.QuoteRepository;
import edu.cnm.deepdive.qod.model.entity.Quote;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//a controller where the return value of the methods in this class will be assumed to be the actual body that goes back to the client
@RequestMapping("/quotes") //gives mapping onto the base url
public class QuoteController {

  private QuoteRepository quoteRepository;

  //explicit dependency injection -- "we'd really like an object of this type" sort of thing


  @Autowired
  public QuoteController(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Quote> list(){
    return quoteRepository.findAllByOrderByText();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quote> post(@RequestBody Quote quote){
    quoteRepository.save(quote);
    return ResponseEntity.created(null).body(quote);
  }
}
