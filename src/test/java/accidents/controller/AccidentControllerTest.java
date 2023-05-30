package accidents.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import accidents.Job4jAccidentsApplication;
import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.model.Rule;
import accidents.service.AccidentService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = Job4jAccidentsApplication.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @MockBean
    AccidentService accidentService;

    @Autowired
    private MockMvc mockMvc;

    private final List<Accident> accList = List.of(
            new Accident(1, "ParkingDB", "I saw violation of parking", "address 1",
                    AccidentType.builder()
                            .id(1)
                            .name("Parking")
                            .build(),
                    Set.of(
                            Rule.builder()
                                .id(1)
                                .name("1.1.1 Parking violation")
                                .build(),
                            Rule.builder()
                                .id(3)
                                .name("1.3.1 Reckless driving")
                                .build())),

            new Accident(2, "Traffic lite", "I saw violation at crossroad", "address 2",
                    AccidentType.builder()
                            .id(2)
                            .name("Traffic lite")
                            .build(),
                    Set.of(
                            Rule.builder()
                                .id(1)
                                .name("1.1.1 Parking violation")
                                .build(),
                            Rule.builder()
                                .id(2)
                                .name("1.2.1 Running a red light")
                                .build(),
                            Rule.builder()
                                .id(3)
                                .name("1.3.1 Reckless driving")
                                .build())),
            new Accident(3, "Crosswalk", "I saw violation on crosswalk", "address 3",
                    AccidentType.builder()
                                .id(3)
                                .name("Crosswalk")
                                .build(),
                    Set.of(
                            Rule.builder()
                                .id(2)
                                .name("1.2.1 Running a red light")
                                .build(),
                            Rule.builder()
                                .id(3)
                                .name("1.3.1 Reckless driving")
                                .build()))
    );

    private final Set<Rule> testRules = Set.of(
            Rule.builder()
                    .id(1)
                    .name("1.1.1 Parking violation")
                    .build(),
            Rule.builder()
                    .id(3)
                    .name("1.3.1 Reckless driving")
                    .build());

    private final Accident testAcc = Accident.builder()
            .id(55)
            .name("testname")
            .text("testtext")
            .address("testaddress")
            .accidentType(AccidentType.builder()
                    .id(1)
                    .name("Parking")
                    .build())
            .build();

    @Test
    void shouldCreateMockMVC() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldBlockMockMVCWithoutMockUser() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser
    void showAllAccidentsThenReturnDefaultMessage() throws Exception {
        when(accidentService.findAllHBM())
                .thenReturn(accList);

        this.mockMvc.perform(get("/accidents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/accidents"));
    }

    @Test
    @WithMockUser
    void returnDefaultMessageOnSave() throws Exception {
        this.mockMvc.perform(get("/accidents/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accidents/createAccident"));
    }

    @Test
    @WithMockUser
    void whenFindByIdSuccessful() throws Exception {
        when(accidentService.findById(1))
                .thenReturn(Optional.ofNullable(accList.get(1)));
    }

    @Test
    @WithMockUser
    void returnFailMessageOnInfo() throws Exception {
        this.mockMvc.perform(get("/accidents/info/{id}", 1111))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accidents/fail"));
    }

    @Test
    @WithMockUser
    void returnFailMessageOnUpdate() throws Exception {
        this.mockMvc.perform(get("/accidents/edit/{id}", 1111))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accidents/fail"));
    }

    @Test
    @WithMockUser
    void returnFailMessageOnDelete() throws Exception {
        this.mockMvc.perform(get("/accidents/delete/{id}", 1111))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accidents/fail"));
    }
}