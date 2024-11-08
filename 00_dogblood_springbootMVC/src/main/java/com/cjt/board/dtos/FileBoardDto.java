package com.cjt.board.dtos;

import org.apache.ibatis.type.Alias;

@Alias(value = "fileBoardDto")
public class FileBoardDto {

	private int fileId;
	private int boardId;
	private String origin_filename;
	private String stored_filename;
	
	public FileBoardDto() {
		super();
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getOrigin_filename() {
		return origin_filename;
	}

	public void setOrigin_filename(String origin_filename) {
		this.origin_filename = origin_filename;
	}

	public String getStored_filename() {
		return stored_filename;
	}

	public void setStored_filename(String stored_filename) {
		this.stored_filename = stored_filename;
	}

	@Override
	public String toString() {
		return "FileBoardDto [fileId=" + fileId + ", boardId=" + boardId + ", origin_filename=" + origin_filename
				+ ", stored_filename=" + stored_filename + "]";
	}
	
	public FileBoardDto(int fileId, int boardId, String origin_filename, String stored_filename) {
		super();
		this.fileId = fileId;
		this.boardId = boardId;
		this.origin_filename = origin_filename;
		this.stored_filename = stored_filename;
	}


	
}
