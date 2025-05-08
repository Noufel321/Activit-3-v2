package com.example.hopital.web;

import com.example.hopital.entities.Patient;
import com.example.hopital.repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping("/user/index")
    public String index(Model model , @RequestParam(name = "page",defaultValue = "0")int page, @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kw ){
        Page<Patient> patientPage=patientRepository.findByNomContains(kw,PageRequest.of(page,size));
        model.addAttribute("ListPatients",patientPage);
        model.addAttribute("pages",new int[patientPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "patients";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

@GetMapping("/admin/patients")
@ResponseBody
public List<Patient> patientList (){
    return patientRepository.findAll();
}
@GetMapping("/admin/formPatients")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
}
//@PostMapping("/admin/save")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
//public String save(Model model, @Valid Patient patient, BindingResult bindingResult,@RequestParam(defaultValue = "0") String keyword,@RequestParam(defaultValue = "") int page){
//        if (bindingResult.hasErrors())return "formPatients";
//        patientRepository.save(patient);
//        return "redirect:/index?page="+page+"&keyword="+keyword;
//}
@PostMapping("/admin/save")
public String save(Model model , @Valid Patient patient, BindingResult bindingResult ,
                   @RequestParam(defaultValue = "0") int page ,
                   @RequestParam(defaultValue ="") String keyword)

{
    if(bindingResult.hasErrors()) return "formPatients";
    patientRepository.save(patient);

    return "redirect:/user/index?page="+page+"&keyword="+keyword;
}
@GetMapping("/admin/editPatient")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public String editPatient(Model model,Long id,String keyword,int page){
    Patient patient=patientRepository.findById(id).orElse(null);
    if (patient==null) throw new RuntimeException("Patient introuvable");
    model.addAttribute("patient",patient);
    model.addAttribute("page",page);
    model.addAttribute("keyword",keyword);
    return "editPatient";
}

}
