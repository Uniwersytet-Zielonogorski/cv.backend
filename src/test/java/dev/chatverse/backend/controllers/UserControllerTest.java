// package dev.chatverse.backend.controllers;

// import dev.chatverse.backend.config.WebSecurityConfig;
// import dev.chatverse.backend.services.CustomOAuth2UserService;
// import dev.chatverse.backend.services.UserService;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(controllers = UserController.class)
// @AutoConfigureMockMvc
// @Import({WebSecurityConfig.class})
// class UserControllerTest {
//     @Autowired
//     private MockMvc mockMvc;
//     @MockBean
//     private UserService userService;
//     @MockBean
//     private CustomOAuth2UserService customOAuth2UserService;

//     @Test
//     void redirect() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders
//                         .get("/login")
//                         .accept(MediaType.APPLICATION_JSON)

//                 )
//                 .andExpect(status().is2xxSuccessful())
//                 .andDo(MockMvcResultHandlers.print());
//     }

//     @Test
//     void unAuth() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders
//                         .get("/v3/api-docs")
//                         .accept(MediaType.APPLICATION_JSON)

//                 )
//                 .andExpect(status().is(200))
//                 .andDo(MockMvcResultHandlers.print());
//     }

//     @Test
//     void unAuth1() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders
//                         .get("/me")
//                         .accept(MediaType.APPLICATION_JSON)

//                 )
//                 .andExpect(status().is(401))
//                 .andDo(MockMvcResultHandlers.print());
//     }

//     @Test
//     void main() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders
//                         .get("/")
//                         .accept(MediaType.APPLICATION_JSON)

//                 )
//                 .andExpect(status().is(401))
//                 .andDo(MockMvcResultHandlers.print());
//     }
// }