package com.example.assignment;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;

@RestController
@RequestMapping("/forms")
public class FormController {

    private static final Logger logger = LoggerFactory.getLogger(FormController.class);

    @Autowired
    private FormRepository formRepository;

    @GetMapping
    public List<FormDTO> getAllForms() {
        return formRepository.findAll()
                .stream()
                .map(Mapper::toFormDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public FormDTO createForm(@RequestBody FormDTO formDTO) {
        Form form = Mapper.toFormEntity(formDTO);
        Form savedForm = formRepository.save(form);
        return Mapper.toFormDTO(savedForm);
    }

    @GetMapping("/{id}")
    public FormDTO getFormById(@PathVariable Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with ID: " + id));
        return Mapper.toFormDTO(form);
    }

    @PutMapping("/{id}")
    public FormDTO updateForm(@PathVariable Long id, @RequestBody FormDTO formDTO) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with ID: " + id));

        form.setName(formDTO.getName());
        form.setPublished(formDTO.isPublished());
        Form updatedForm = formRepository.save(form);
        return Mapper.toFormDTO(updatedForm);
    }

    @DeleteMapping("/{id}")
    public void deleteForm(@PathVariable Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with ID: " + id));
        formRepository.delete(form);
    }

    @GetMapping("/{id}/fields")
    public List<FieldDTO> getFieldsByFormId(@PathVariable Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with ID: " + id));
        return form.getFields()
                .stream()
                .map(Mapper::toFieldDTO)
                .collect(Collectors.toList());
    }

    // @PutMapping("/{id}/fields")
    // public FormDTO updateFields(@PathVariable Long id, @RequestBody
    // List<FieldDTO> fieldDTOs) {
    // Form form = formRepository.findById(id)
    // .orElseThrow(() -> new ResourceNotFoundException("Form not found with ID: " +
    // id));

    // List<Field> fields = fieldDTOs.stream()
    // .map(Mapper::toFieldEntity)
    // .collect(Collectors.toList());
    // System.out.println(fields);
    // fields.forEach(field -> field.setForm(form));
    // form.setFields(fields);
    // Form updatedForm = formRepository.save(form);
    // return Mapper.toFormDTO(updatedForm);
    // }

    @PutMapping("/{id}/fields")
    public FormDTO updateFields(@PathVariable Long id, @RequestBody List<FieldDTO> fieldDTOs) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with ID: " + id));
    
        form.getFields().clear();
    
        List<Field> fields = fieldDTOs.stream()
                .map(dto -> {
                    Field field = Mapper.toFieldEntity(dto);
                    field.setForm(form);
                    return field;
                })
                .collect(Collectors.toList());
            
        form.getFields().addAll(fields);
        Form updatedForm = formRepository.save(form);
        return Mapper.toFormDTO(updatedForm);
    }

    @PostMapping("/{id}/publish")
    public FormDTO togglePublishForm(@PathVariable Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with ID: " + id));

        form.setPublished(!form.isPublished());
        Form updatedForm = formRepository.save(form);
        return Mapper.toFormDTO(updatedForm);
    }

    @GetMapping("/published")
    public List<Form> getPublishedForms() {
        return formRepository.findAllByPublished(true);
    }
}
