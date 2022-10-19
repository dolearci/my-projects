proc isim_script {} {

   add_divider "Signals of the Vigenere Interface"
   add_wave_label "" "CLK" /testbench/clk
   add_wave_label "" "RST" /testbench/rst
   add_wave_label "-radix ascii" "DATA" /testbench/tb_data
   add_wave_label "-radix ascii" "KEY" /testbench/tb_key
   add_wave_label "-radix ascii" "CODE" /testbench/tb_code

   add_divider "Vigenere Inner Signals"
	
	#Moje signaly
	add_wave_label "-radix dec" "PPocet" /testbench/uut/posunutie_pocet
	add_wave_label "-radix dec" "PMinus" /testbench/uut/posunutie_minus
	add_wave_label "-radix dec" "PPlus" /testbench/uut/posunutie_plus	
	add_wave_label "" "stav" /testbench/uut/stav
	add_wave_label "" "vystup" /testbench/uut/vystup
	

   run 8 ns
}
