package homework.homework.gui;

import java.io.Console;
import java.util.List;

import homework.homework.dto.PhoneBookDto;
import homework.homework.service.PhoneBookService;
import homework.homework.serviceImpl.PhoneBookServiceImpl;

public class ConsoleApp {

	PhoneBookService phoneBookService = new PhoneBookServiceImpl();
	static Console con = System.console();
	static final String help = "Supported opertions are: help, add name phone, remove phone, list, search name, exit\n";
	
	public static void main(String[] args) throws Exception {
		ConsoleApp app  = new ConsoleApp();
		//new H2EntityManager().init();
		
		if(args.length > 0) {
			String line = args[0];
			for(int i = 1; i < args.length; i++) line = line + " " + args[i];
			app.command(line);
		}else {
			
			con.printf("Type help for supported operations. ");
			
			con.printf(help);
			String line;
			while (true) {
				 line = con.readLine();
				 app.command(line);
				
			}
		}
	}
	
	void command(String line) {
		if(line.startsWith("help")) {
			con.printf(help);
			
		}else if(line.matches("add \\w+ \\d+")) {
			String[] a = line.split(" ");
			phoneBookService.addEntry(new PhoneBookDto(a[1], a[2]));
			;
		}else if(line.matches("remove \\d+")) {
			String[] a = line.split(" ");
			PhoneBookDto phone = phoneBookService.findByPhone(a[1]);
			phoneBookService.removeEntry(phone);
		}else if(line.matches("list")) {
			List<PhoneBookDto> list = phoneBookService.list();
			for(PhoneBookDto dto: list) {
				System.out.println(dto.getName() + " " + dto.getPhone());
			}
		}else if(line.matches("search \\w+")) {
			String name = line.split(" ")[1];
			List<PhoneBookDto> list = phoneBookService.findByName(name);
			for(PhoneBookDto dto: list) {
				System.out.println(dto.getName() + " " + dto.getPhone());
			}
		}else if (line.startsWith("exit")) {
			System.exit(0);
		}else {
			con.printf(help);
		}
	}

}
