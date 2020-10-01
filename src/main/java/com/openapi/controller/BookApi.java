package com.openapi.controller;

import com.openapi.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Tag(name = "book", description = "the book API")
@RequestMapping("/api/v1/books")
public interface BookApi {

  @Operation(summary = "Find book by ID", description = "Returns a single book", tags = { "book" })
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Book.class))),
      @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
      @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
  @RequestMapping(value = "/{id}", produces = { "application/json",  "application/vnd.api+json"}, method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Book> findById(
      @Parameter(description = "ID of book", required = true)
      @PathVariable long id,
      @NotNull @Parameter(description = "select which kind of data to fetch", required = true)
      @Valid @RequestHeader(value="bookAuthorization", required = true) String bookAuthorization)
      throws Exception;

  @Operation(summary = "Get books", description = "Returns a books collection", tags = { "book" })
  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Book> findBooks();

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book updateBook(@PathVariable("id") final String id, @RequestBody final Book book);

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book patchBook(@PathVariable("id") final String id, @RequestBody final Book book);

  @Operation(summary = "Create book", description = "This can only be done by the logged in book.", tags = { "book" })
  @ApiResponses(value = { @ApiResponse(description = "successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = Book.class)) }) })
  @PostMapping(value = "/", consumes = { "application/json", "application/xml", "application/x-www-form-urlencoded" })
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Book> postBook(
      @NotNull
      @Parameter(description = "Created book object", required = true)
      @Valid @RequestBody Book body,
      @NotNull @Parameter(description = "select which kind of data to fetch", required = true)
      @Valid @RequestHeader(value="bookAuthorization", required = true) String bookAuthorization)
      throws Exception;

  @RequestMapping(method = RequestMethod.HEAD, value = "/")
  @ResponseStatus(HttpStatus.OK)
  public Book headBook();

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public long deleteBook(@PathVariable final long id);

}
