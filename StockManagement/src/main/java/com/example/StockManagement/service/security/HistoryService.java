package com.example.StockManagement.service.security;

import com.example.StockManagement.data.model.security.model.History;
import com.example.StockManagement.data.model.security.model.User;
import com.example.StockManagement.repository.security.HistoryRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final AuthenticationService authService;

    public HistoryService(HistoryRepository historyRepository, AuthenticationService authService) {
        this.historyRepository = historyRepository;
        this.authService = authService;
    }

    public History createHistory(History history) {
        User user = authService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        boolean exists = historyRepository.existsByUserAndTitleAndStartDateAndEndDate(
                user, history.getTitle(), history.getStartDate(), history.getEndDate());
        if (exists) {
            throw new IllegalArgumentException("Duplicate history record. This record already exists.");
        }
        history.setUser(user);
        return historyRepository.save(history);
    }

    public List<History> getHistoriesByUser(User user) {
        return historyRepository.findByUser(user);
    }

    public History updateHistory(Long id, History updatedHistory) {
        History existingHistory = historyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("History with ID " + id + " not found"));
        existingHistory.setStartDate(updatedHistory.getStartDate());
        existingHistory.setEndDate(updatedHistory.getEndDate());
        existingHistory.setTitle(updatedHistory.getTitle());
        existingHistory.setDescription(updatedHistory.getDescription());
        existingHistory.setInstitutionName(updatedHistory.getInstitutionName());
        existingHistory.setRoleOrDegree(updatedHistory.getRoleOrDegree());
        return historyRepository.save(existingHistory);
    }

    public void deleteHistory(Long id) {
        if (!historyRepository.existsById(id)) {
            throw new IllegalArgumentException("History with ID " + id + " does not exist");
        }
        historyRepository.deleteById(id);
    }

}

