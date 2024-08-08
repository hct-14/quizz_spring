package com.example.quizz.Service;

import com.example.quizz.Dto.ResHistoryDTO.ResHistoryDTO;
import com.example.quizz.Dto.ResultPaginationDTO;
import com.example.quizz.Entity.History;
import com.example.quizz.Repository.HistoryRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    public Optional<History> getHistory(int id) {
        return this.historyRepository.findById(id);
    }

    public History createHistory(History history) {
        return this.historyRepository.save(history);
    }

    public ResultPaginationDTO getAllHistory(Specification<History> spec, Pageable pageable) {
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Page<History> page = historyRepository.findAll(spec, pageable);
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber()+1);
        mt.setPageSize(pageable.getPageSize());
        mt.setTotal(page.getTotalElements());
        mt.setPages(page.getTotalPages());
        rs.setMeta(mt);

        List<ResHistoryDTO> list = page.getContent().stream().map(item-> new ResHistoryDTO(
                item.getId(),
                item.getTotalQuestions(),
                item.getTotalCorrect(),
                new ResHistoryDTO.QuizzHis(
                        item.getQuizz() != null ? item.getQuizz().getId() : 0,
                        item.getQuizz() !=null ?item.getQuizz().getName(): null,
                        item.getQuizz() !=null ?item.getQuizz().getDescription(): null),



                new ResHistoryDTO.UserHis(
                        item.getUser() != null ? item.getUser().getId() : 0,
                        item.getUser() != null ? item.getUser().getName() : null)

                )).collect(Collectors.toList());
        rs.setResult(list);
        return rs;

    }

}
