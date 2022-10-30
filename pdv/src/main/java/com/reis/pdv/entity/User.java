package com.reis.pdv.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
	private Long id;
	private String name;
	private boolean isEnabled;
}
