package com.multiplex;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.multiplex.document.Multiplex;
import com.multiplex.document.Screen;
import com.multiplex.repository.MultiplexRepository;
import com.multiplex.repository.ScreenRepository;

@Component
public class StarterData implements CommandLineRunner {
	
	@Autowired
	private MultiplexRepository multiplexRepository;
	
	@Autowired
	private ScreenRepository screenRepository;

	@Override
	public void run(String... args) throws Exception {
		saveDefaultMultiplexWithScreens(new Multiplex(null, "Cinepolis: Viviana Mall, Thane", 
				"Viviana Mall, Eastern Express Highway, "
				+ "Laxmi Nagar, Thane (West), Thane, Maharashtra 400606, India", null));
		saveDefaultMultiplexWithScreens(new Multiplex(null, "Cinemax: 4DX Infiniti Mall, Malad (W)", 
				"Survey No. 504, New Link Road, Malad (W), Near D-Mart, Mumbai, "
				+ "Maharashtra 400065, India", null));
		saveDefaultMultiplexWithScreens(new Multiplex(null, "PVR ICON: Infiniti Andheri (W)", 
				"Infinity Mall, New Link Road, Opposite Crystal Plaza, Next To Citi Mall, "
				+ "Andheri (W), Near Lotus Petrol Pump, Mumbai, Maharashtra 400053, India", null));
		saveDefaultMultiplexWithScreens(new Multiplex(null, "Cinemax: Kalyan", 
				"3rd Floor, Spring Avenue Mall, Survey No. 61, Club Road, Off Barave Road, "
				+ "Khadakpada, Kalyan (West), Kalyan, Maharashtra 421301, India", null));
		saveDefaultMultiplexWithScreens(new Multiplex(null, "Carnival: Huma, Kanjurmarg", 
				"Huma Mall, LBS Road, Kanjurmarg (W), Near Kanjurmarg Railway Station, Mumbai, "
				+ "Maharashtra 400078, India", null));
		saveDefaultMultiplexWithScreens(new Multiplex(null, "PVR: Lodha Xperia, Palava", 
				"3rd Floor, Xperia Mall, Kalyan-Shilphata Road, Opposit Lodha World School, "
				+ "Dombivali (E), Mumbai, Maharashtra 421204, India", null));
	}
	
	private void saveDefaultMultiplexWithScreens(Multiplex multiplex) {
		multiplexRepository.save(multiplex);
		IntStream.range(0, 4)
		.forEach(i -> screenRepository.save(new Screen(null, "A" + (i + 1), multiplex.getId())));
	}

}
