import { type CrewDto } from '@/server/data/dto/crew-dto';

import { type CastDto } from './cast-dto';

export type CreditsDtoArray = {
	id: number;
	cast: CastDto[];
	crew: CrewDto[];
};
