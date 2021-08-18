package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.onltest.entity.Subject;

public interface SubjectService {
	Page<Subject> getSubjectsIsExistedWithPagination(Pageable pageable);

    Page<Subject> getSubjectsIsExistedWithQueryAndPagination(String query, Pageable pageable);
}
