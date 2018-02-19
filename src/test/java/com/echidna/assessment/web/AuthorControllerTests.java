package com.echidna.assessment.web;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.echidna.assessment.domain.Author;
import com.echidna.assessment.service.AuthorService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthorRestController.class)
public class AuthorControllerTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private AuthorService authorService;

  @Test
  public void givenAuthors_whenGetAuthors_thenReturnJsonArray() throws Exception {
    List<Author> authors = Arrays.asList(new Author(1, "R.K. Narayan", "Author of Malgudi Days"),
        new Author(2, "Rabindranath Tagore", "Author of Gitanjali"),
        new Author(3, "Leo Tolstoy", "Russian writer"),
        new Author(4, "Premchand", "Author in Hindi-Urdu"),
        new Author(5, "Ruskin Bond", "Author in English"));

    given(authorService.listAll()).willReturn(authors);

    mvc.perform(get("/authors")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)))
        .andExpect(jsonPath("$[0].name", is(authors.get(0).getName())));
  }
}