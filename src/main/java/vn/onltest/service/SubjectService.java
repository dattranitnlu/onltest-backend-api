package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;
import vn.onltest.model.entity.Subject;
import vn.onltest.model.projection.SubjectListView;

import java.util.List;

public interface SubjectService {
    @Transactional(readOnly = true)
	Page<Subject> getSubjectsIsExistedWithPagination(Pageable pageable);

    @Transactional(readOnly = true)
    Page<Subject> getSubjectsIsExistedWithQueryAndPagination(String query, Pageable pageable);

    @Transactional(readOnly = true)
    List<SubjectListView> getSubjectsIsExistedNotPagination();
}
