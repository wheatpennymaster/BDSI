#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fasta.c"

char * read_file(void)
{
	char * file_contents;
	long input_file_size;
	FILE *input_file = fopen("/Users/thomas/Desktop/w303.fasta","rb");
	fseek(input_file,0,SEEK_END);
	input_file_size = ftell(input_file);
	rewind(input_file);
	file_contents = malloc(input_file_size * (sizeof(char)));
	fread(file_contents,sizeof(char),input_file_size,input_file);
	fclose(input_file);
	return file_contents;
}

void n(void)
{
	printf("\n");
}


/*
char * help_get_gene(char * s)
{
	char * r = malloc((strlen(s)-1) * sizeof(char));
	for (int i=0;i<strlen(r);i++)
	{
		r[i]=s[i+1];
	}
	
	return r;
}

void get_gene(char * s)
{
	struct chr gene[16];
	
	char * pch;
	pch = strtok(s,"\n");
	int c = -1;
	while(pch !=NULL)
	{
		if(pch[0]=='>')
		{
			printf("%s\n",pch);
			c++;
			gene[c].chr_name=help_get_gene(pch);
			pch = strtok(NULL,"\n");
			gene[c].seq=" ";
		}
		
		
		char * seq = gene[c].seq;
		gene[c].seq = malloc((strlen(seq) + strlen(pch)) * sizeof(char));
		strcpy(gene[c].seq,seq);
		strncat(gene[c].seq,pch,strlen(pch));
				
		pch = strtok(NULL,"\n");
	}
	printf("%s\n",gene[0].chr_name);

}
*/

int main()
{
	struct chr * test;
	test = read_fasta("w303.fasta");

	
	
	
	
	

}