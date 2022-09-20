package com.bridgelabz.fundoonote.note.service;

import java.util.List;

import com.bridgelabz.fundoonote.note.dto.NoteDto;
import com.bridgelabz.fundoonote.note.exception.NoteException;
import com.bridgelabz.fundoonote.note.utility.NoteResponse;

public interface INoteService {

	NoteResponse addNewNote(NoteDto noteDto, String token) throws NoteException;

	NoteResponse deleteNote(int id)throws NoteException;

	List<NoteDto> getAll();

	NoteResponse updateNote(int id, NoteDto noteDto);

}