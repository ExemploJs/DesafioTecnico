package com.example.user.service;

import com.example.exception.UserNotFoundException;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.request.UserRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    private UserRequest userRequest;

    @Captor
    private ArgumentCaptor<User> userCaptor;
    private String userName;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void create() {
        givenAnUserRequest();
        whenCreate();
        thenShouldSaveCorrectly();
    }

    @Test
    public void findByUserName() {
        givenAnUserName();
        whenFindByUserName();
        thenShouldFindByUserNameCorrectly();
    }

    @Test
    public void findByUserNameButNothingWasFound() {
        givenAnUserName();
        thenShouldExpectError();
        whenFindByUserNameErr();
    }

    @Test
    public void findAll() {
        whenFindAll();
    }

    private void whenFindAll() {
        when(this.userRepository.findAll()).thenReturn(List.of(new User("test1"), new User("test2")));
        this.userService.findAll();
    }

    private void givenAnUserRequest() {
        this.userRequest = new UserRequest();
        this.userRequest.setUserName("create test");
    }

    private void givenAnUserName() {
        this.userName = "testUserName";
    }

    private void whenCreate() {
        this.userService.create(this.userRequest);
    }

    private void whenFindByUserName() {
        when(this.userRepository.findByUserName(this.userName)).thenReturn(new User("testUserName"));
        this.userService.findByUserName(this.userName);
    }

    private void whenFindByUserNameErr() {
        this.userService.findByUserName(this.userName);
    }

    private void thenShouldSaveCorrectly() {
        verify(this.userRepository, times(1)).save(this.userCaptor.capture());
        final User actual = this.userCaptor.getValue();

        assertEquals("create test", actual.getUserName());
    }

    private void thenShouldFindByUserNameCorrectly() {
        verify(this.userRepository, times(1)).findByUserName(this.userName);
    }

    private void thenShouldExpectError() {
        this.expectedException.expect(UserNotFoundException.class);
        this.expectedException.expectMessage("Usuário não encontrado/cadastrado!");
    }
}