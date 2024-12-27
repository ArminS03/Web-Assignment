package com.example.assignment;

public class Mapper {

    public static FormDTO toFormDTO(Form form) {
        FormDTO dto = new FormDTO();
        dto.setId(form.getId());
        dto.setName(form.getName());
        dto.setPublished(form.isPublished());
        return dto;
    }

    public static FieldDTO toFieldDTO(Field field) {
        FieldDTO dto = new FieldDTO();
        dto.setId(field.getId());
        dto.setName(field.getName());
        dto.setLabel(field.getLabel());
        dto.setType(field.getType().toString());
        dto.setDefaultValue(field.getDefaultValue());
        return dto;
    }

    public static Form toFormEntity(FormDTO dto) {
        Form form = new Form();
        form.setId(dto.getId());
        form.setName(dto.getName());
        form.setPublished(dto.isPublished());
        return form;
    }

    public static Field toFieldEntity(FieldDTO dto) {
        Field field = new Field();
        field.setId(dto.getId());
        field.setName(dto.getName());
        field.setLabel(dto.getLabel());
        field.setType(FieldType.valueOf(dto.getType()));
        field.setDefaultValue(dto.getDefaultValue());
        return field;
    }
}
