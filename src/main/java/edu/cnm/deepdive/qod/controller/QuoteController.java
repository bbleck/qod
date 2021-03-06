package edu.cnm.deepdive.qod.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.cnm.deepdive.qod.model.dao.QuoteRepository;
import edu.cnm.deepdive.qod.model.entity.Quote;
import edu.cnm.deepdive.qod.view.Nested;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController//a controller where the return value of the methods in this class will be assumed to be the actual body that goes back to the client
@RequestMapping("/quotes") //gives mapping onto the base url
@JsonView(Nested.class)
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

  @GetMapping(value = "searchByText", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Quote> searchText(@RequestParam("fragment") String fragment){
    return quoteRepository.findAllByTextContainingOrderByText(fragment);
  }

  @GetMapping(value = "searchBySourceText", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Quote> searchSource(@RequestParam("fragment") String fragment){
    return quoteRepository.findAllBySourceContainingOrderBySourceAscTextAsc(fragment);
  }

//  @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
//  public List<Quote> search(@RequestParam("sourceFrag") String sourceFrag, @RequestParam("textFrag") String textFrag){
//    return quoteRepository.findAllBySourceContainingAndTextContainingOrderBySourceAscTextAsc(sourceFrag, textFrag);
//  }

  @GetMapping(value = "{quoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Nested.class)
  public Quote get(@PathVariable("quoteId") UUID quoteId){
    return quoteRepository.findById(quoteId).get();
  }

  @GetMapping(value = "random", produces = MediaType.APPLICATION_JSON_VALUE)
  @JsonView(Nested.class)
  public Quote random(){
    return quoteRepository.findRandom().get();
  }

  @DeleteMapping(value = "{quoteId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("quoteId") UUID quoteId) {
    quoteRepository.delete(get(quoteId));
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Quote not found")
  @ExceptionHandler(NoSuchElementException.class)
  public void notFound(){

  }
}
