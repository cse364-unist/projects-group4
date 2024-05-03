package com.example.db.dal;

import java.util.List;

import com.example.db.model.Summary;

public interface SummaryDAL {

	List<Summary> getAllSummaries();

	Summary getSummaryById(String id);

	Summary addNewSummary(Summary summary);
}