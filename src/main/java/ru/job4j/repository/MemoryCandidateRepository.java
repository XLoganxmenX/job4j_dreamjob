package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Candidate;
import java.util.*;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Petr Ivanov", "Intern Java Developer"));
        save(new Candidate(0, "Vasiliy Habar", "Junior Java Developer"));
        save(new Candidate(0, "Elena Samovar", "Junior+ Java Developer"));
        save(new Candidate(0, "Stanislav Zelen", "Middle Java Developer"));
        save(new Candidate(0, "Vladimir Pupkin", "Middle+ Java Developer"));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(
                        oldCandidate.getId(),
                        candidate.getName(),
                        candidate.getDescription())
        ) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
