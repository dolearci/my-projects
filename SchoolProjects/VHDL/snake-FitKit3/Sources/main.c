/*
 * Copyright (c) 2015, Freescale Semiconductor, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * o Redistributions of source code must retain the above copyright notice, this list
 *   of conditions and the following disclaimer.
 *
 * o Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * o Neither the name of Freescale Semiconductor, Inc. nor the names of its
 *   contributors may be used to endorse or promote products derived from this
 *   software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include "MK60D10.h"
#include <stdbool.h>

//display riadky (Piny)
//26, 24, 9, 25, 28, 7, 27, 29 (riadok0 - riadok7)
int display_rows[8] = {
	(((1)<<(26 & 0x1Fu))), (((1)<<(24 & 0x1Fu))), (((1)<<(9 & 0x1Fu))), (((1)<<(25 & 0x1Fu))),
	(((1)<<(28 & 0x1Fu))), (((1)<<(7 & 0x1Fu))), (((1)<<(27 & 0x1Fu))), (((1)<<(29 & 0x1Fu)))
};

//Piny k jednotlivym Tlacitkam
//10, 11, 12, 26, 27
#define button2 0x400 // RIGHT = 2
#define button6 0x800 // RESET = 6
#define button3 0x1000 // DOWN = 3
#define button5 0x4000000 // UP = 5
#define button4 0x8000000 // LEFT = 4

//Premenne pre zobrazovanie hada, decoder, tlacitko, bool hit
int snake [7][2];
int column_select[4];
int tmp;
int button;
bool snake_hit = false;

//Funckia pre generovanie delayu
void delayloop(int delay)
{
	for(int i = 0; i < delay; i++) {}
}

void Init(void)
{
	//MCU
	WDOG_STCTRLH &= ~WDOG_STCTRLH_WDOGEN_MASK;
	MCG_C4 |= ( MCG_C4_DMX32_MASK | MCG_C4_DRST_DRS(0x01) );
	SIM_CLKDIV1 |= SIM_CLKDIV1_OUTDIV1(0x00);

	//Ports clocks ON
    SIM->SCGC5 = SIM_SCGC5_PORTB_MASK | SIM_SCGC5_PORTE_MASK | SIM_SCGC5_PORTA_MASK;

    //Tlacitka 2,6,3,5,4
    PORTE->PCR[10] = PORT_PCR_MUX(0x01);
    PORTE->PCR[11] = PORT_PCR_MUX(0x01);
    PORTE->PCR[12] = PORT_PCR_MUX(0x01);
    PORTE->PCR[26] = PORT_PCR_MUX(0x01);
    PORTE->PCR[27] = PORT_PCR_MUX(0x01);

    //Riadky displayu riadok0 - riadok7
    PORTA->PCR[26] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[24] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[9] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[25] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[28] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[7] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[27] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[29] = (0|PORT_PCR_MUX(0x01));

    //Riadiace piny A0 - A3
    PORTA->PCR[8] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[10] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[6] = (0|PORT_PCR_MUX(0x01));
    PORTA->PCR[11] = (0|PORT_PCR_MUX(0x01));


    //GPIO funkcie
    PORTE->PCR[28] = ( 0|PORT_PCR_MUX(0x01) );

    //PTX porty - vytupy
    PTA->PDDR = GPIO_PDDR_PDD(0x3F000FC0);
    PTB->PDDR = GPIO_PDDR_PDD(0x3C);
    PTE->PDDR = GPIO_PDDR_PDD(((1)<<(28 & 0x1Fu)));

    //LPTMR0
    SIM_SCGC5 |= SIM_SCGC5_LPTIMER_MASK;
    LPTMR0_CSR &= ~LPTMR_CSR_TEN_MASK;
    LPTMR0_PSR = (LPTMR_PSR_PRESCALE(0) | LPTMR_PSR_PBYP_MASK | LPTMR_PSR_PCS(1));
    LPTMR0_CMR = 0x200;
    LPTMR0_CSR =(LPTMR_CSR_TCF_MASK | LPTMR_CSR_TIE_MASK);
    NVIC_EnableIRQ(LPTMR0_IRQn);
    PORTE->PCR[10] = (PORT_PCR_ISF(0x01) | PORT_PCR_IRQC(0x0A) | PORT_PCR_MUX(0x01) | PORT_PCR_PE(0x01) | PORT_PCR_PS(0x01));
    PORTE->PCR[11] = (PORT_PCR_ISF(0x01) | PORT_PCR_IRQC(0x0A) | PORT_PCR_MUX(0x01) | PORT_PCR_PE(0x01) | PORT_PCR_PS(0x01));
    PORTE->PCR[12] = (PORT_PCR_ISF(0x01) | PORT_PCR_IRQC(0x0A) | PORT_PCR_MUX(0x01) | PORT_PCR_PE(0x01) | PORT_PCR_PS(0x01));
    PORTE->PCR[26] = (PORT_PCR_ISF(0x01) | PORT_PCR_IRQC(0x0A) | PORT_PCR_MUX(0x01) | PORT_PCR_PE(0x01) | PORT_PCR_PS(0x01));
    PORTE->PCR[27] = (PORT_PCR_ISF(0x01) | PORT_PCR_IRQC(0x0A) | PORT_PCR_MUX(0x01) | PORT_PCR_PE(0x01) | PORT_PCR_PS(0x01));
    NVIC_ClearPendingIRQ(PORTE_IRQn);
    NVIC_EnableIRQ(PORTE_IRQn);
    LPTMR0_CSR |= LPTMR_CSR_TEN_MASK;
}

void LPTMR0_IRQHandler(void)
{
    LPTMR0_CMR = 0x200;
    LPTMR0_CSR |=  LPTMR_CSR_TCF_MASK;
}

void PORTE_IRQHandler(void) {
	delayloop(20000);

	if (PORTE->ISFR & button2 && ((GPIOE_PDIR & button2) == 0) && button != 4){ // Right
		button = 2;
	} else if (PORTE->ISFR & button3 && ((GPIOE_PDIR & button3) == 0)  && button != 5){ // Down
		button = 3;
	} else if (PORTE->ISFR & button4 && ((GPIOE_PDIR & button4) == 0)  && button != 2){ // Left
		button = 4;
	} else if(PORTE->ISFR & button5 && ((GPIOE_PDIR & button5) == 0)  && button != 3) { // Up
		button = 5;
	} else if (PORTE->ISFR & button6 && ((GPIOE_PDIR & button6) == 0)){ //Reset
		button = 6;
	}
	PORTE->ISFR = button2 | button3 | button4 | button5| button6;
}

void decoder_switch(int column_number)
{
	//A0,A1,A2,A3
	for (int i = 0; i < 4; i++) {
		tmp = column_number / 2;
		column_select[i] = column_number % 2;
		column_number = tmp;

		switch(i) {
			//A0
		    case 0:
				if(((column_select[i]) == 0)) {
					(PTA->PDOR &= ~GPIO_PDOR_PDO((((1)<<(8 & 0x1Fu)))));
				} else{
					(PTA->PDOR |= GPIO_PDOR_PDO((((1)<<(8 & 0x1Fu)))));
				}
				break;

			//A1
			case 1:
				if(((column_select[i]) == 0)){
					(PTA->PDOR &= ~GPIO_PDOR_PDO((((1)<<(10 & 0x1Fu)))));
				} else{
					(PTA->PDOR |= GPIO_PDOR_PDO((((1)<<(10 & 0x1Fu)))));
				}

				break;

			//A2
			case 2:
				if(((column_select[i]) == 0)) {
					(PTA->PDOR &= ~GPIO_PDOR_PDO((((1)<<(6 & 0x1Fu)))));
				} else{
					(PTA->PDOR |= GPIO_PDOR_PDO((((1)<<(6 & 0x1Fu)))));
				}
				break;

			//A3
			case 3:
				if(((column_select[i]) == 0)) {
					(PTA->PDOR &= ~GPIO_PDOR_PDO((((1)<<(11 & 0x1Fu)))));
				} else{
					(PTA->PDOR |= GPIO_PDOR_PDO((((1)<<(11 & 0x1Fu)))));
				}
				break;
			default:
				break;
		}
	}
}

//Start funkcia ktora sa vola pri zaciatku hry
//Vytvara hada na zakladnej pozicii
void start(void) {
	snake_hit = false;
	button = 5;
	for (int i = 0; i < 7; i++) {
		//Start position
		snake[i][0] = 7+i;
		snake[i][1] = 4;
		PTA->PDOR &= ~GPIO_PDOR_PDO(0x3F000280);
		PTA->PDOR |= GPIO_PDOR_PDO(display_rows[snake[0][1]]);
		decoder_switch(snake[i][0]);
		delayloop(50000);
	}
}

//Stara sa o vizualizaciu hada
void show(void) {
	for (int j = 0; j < 7*10; j++) {
		PTA->PDOR &= ~GPIO_PDOR_PDO(0x3F000280);
		PTA->PDOR |= GPIO_PDOR_PDO(display_rows[snake[j%7][1]] );
		decoder_switch(snake[j%7][0]);
		delayloop(10000);
	}
}

//Funckia ktora sa stara o narazenie do hada.
//Nastavi snake_hit na true
void hit(void) {
	for(int i = 1; i < 7; i++){
		if(snake[0][0] == snake[i][0] && snake[0][1] == snake[i][1]) {snake_hit = true;}
	}
}

//Pohyb hada
void move() {
	for (int i = 6; i > 0; i--) {
		snake[i][0] = snake[i - 1][0];
		snake[i][1] = snake[i - 1][1];
	}
}

int main(void)
{
	//Inicializacia
    Init();
    start();

    //Nekonecny cyklus ktory sa stara o beh hry
    for(;;) {
    	//volanie funkcie na zistenie ci had trafil svoje telo
    	hit();
    	if(!snake_hit){ //netrafil
    		//Switch pre kazdy button
    		switch(button) {
    		    	//Right
    		    	case 2:
    		    		move();
    		    		// Ak je na pravom
    		    		if(snake[0][1] == 0) {snake[0][1] = 7;}
    		    		else {snake[0][1] = snake[0][1] - 1;}
    		    		show();
    		    		break;
    		    	//Down
    		    	case 3:
    		    		move();
    		    		// Ak je na spodnej hrane
    		    		if (snake[0][0] == 15) {snake[0][0] = 0;}
    		    		else {snake[0][0] = snake[0][0] + 1;}
    		    		show();
    		    		break;
    		    	//Left
    		    	case 4:
    		    		move();
    		    		//Ak je na lavom kraji
    		    		if(snake[0][1] == 7) {snake[0][1] = 0;}
    		    		else {snake[0][1] = snake[0][1] + 1;}
    		    		show();
    		    		break;
    		    	//Up
    		    	case 5:
    		    		move();
    		    		//Ak je na hornej hrane
    		    		if (snake[0][0] == 0) {snake[0][0] = 15;}
    		    		else {snake[0][0] = snake[0][0] - 1;}
    		    		show();
    		    		break;
    		    	//Reset
    		    	case 6:
    		    		start();
    		    		break;
    		    }
    	}
    	else{ //trafil
    		if(button == 6){start();}
    	}
    	PTE->PDDR &= ~GPIO_PDDR_PDD(((1)<<(28 & 0x1Fu)));
    	delayloop(200000);
    	PTE->PDOR |= GPIO_PDOR_PDO(((1)<<(28 & 0x1Fu)));
    }

    return 0;
}
////////////////////////////////////////////////////////////////////////////////
// EOF
////////////////////////////////////////////////////////////////////////////////
