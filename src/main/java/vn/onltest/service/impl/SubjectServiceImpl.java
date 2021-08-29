package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.onltest.entity.Subject;
import vn.onltest.repository.SubjectRepository;
import vn.onltest.service.SubjectService;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {
	private final SubjectRepository subjectRepository;
	
	@Override
	public Page<Subject> getSubjectsIsExistedWithPagination(Pageable pageable) {
		return subjectRepository.findAllBy(pageable);
	}

	@Override
	public Page<Subject> getSubjectsIsExistedWithQueryAndPagination(String query, Pageable pageable) {
		return subjectRepository.findByCourseNameLike(query, pageable);
	}
	
}
