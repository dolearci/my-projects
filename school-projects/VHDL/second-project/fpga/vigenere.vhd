library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.std_logic_arith.all;
use IEEE.std_logic_unsigned.all;

-- rozhrani Vigenerovy sifry
entity vigenere is
   port(
         CLK : in std_logic;
         RST : in std_logic;
         DATA : in std_logic_vector(7 downto 0);
         KEY : in std_logic_vector(7 downto 0);

         CODE : out std_logic_vector(7 downto 0)
    );
end vigenere;
architecture behavioral of vigenere is

	-- signaly pre posunutie a # --
	signal posunutie_pocet: std_logic_vector(7 downto 0);
	signal posunutie_minus: std_logic_vector(7 downto 0);
	signal posunutie_plus: std_logic_vector(7 downto 0);
	signal hash: std_logic_vector(7 downto 0) := "00100011";
	-- signaly pre posunutie a # --
	
	-- Mealy --
	signal vystup: std_logic_vector(1 downto 0);
	type Tstav is (plus, minus);
	signal stav: Tstav := plus;
	signal Nstav: Tstav := minus;
	-- Mealy --
    

begin
	-- Prva skrinka v obvode kam vstupuje KEY a DATA --
   posuv: process (DATA,KEY) is
	begin
		posunutie_pocet <= KEY - 64;
	end process;
	-- Prva skrinka v obvode kam vstupuje KEY a DATA --
	
	-- skrinka s korekciou - --
	posuv_minus: process (posunutie_pocet,DATA) is
		variable var: std_logic_vector(7 downto 0);
	begin
	var := DATA;
	var := var - posunutie_pocet;
		if (var < 65) then
			posunutie_minus <= var + 26;
		else
			posunutie_minus <= var;
		end if;
	end process;	
	-- skrinka s korekciou - --
	
	-- skrinka s korekciou + --
	posuv_plus: process (posunutie_pocet,DATA) is
		variable var: std_logic_vector(7 downto 0);
	begin
	var := DATA;
	var := var + posunutie_pocet;
		if (var > 90) then
			posunutie_plus <= var - 26;
		else
			posunutie_plus <= var;
		end if;
	end process;	
	-- skrinka s korekciou + --
	
	-- clock --
	clock: process (CLK, RST) is
	begin
		if RST = '1' then
			stav <= plus;
		elsif rising_edge(CLK) then
			stav <= Nstav;
		end if;
	end process;
	-- clock --
	
	-- Mealy -- 
	mealy: process(stav, DATA, RST) is
	 begin
		if (stav = plus) then
			Nstav <= minus;
			vystup <= "01";
		elsif (stav = minus) then
			Nstav <= plus;
			vystup <= "10";
		end if;
		if (RST = '1' or (DATA > 47 and DATA < 58)) then
			vystup <= "00";
		end if;
	end process;
	-- Mealy -- 
	
	-- Multiplexor -- 
	koniec: process(vystup, posunutie_plus, posunutie_minus) is
	begin
		case vystup is
			when "01" =>
				CODE <= posunutie_plus;
			when "10" =>
				CODE <= posunutie_minus;
			when others =>
				CODE <= hash;
		end case;
	end process;
	-- Multiplexor --

end behavioral;
