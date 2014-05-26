#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct chr
{
	char * chr_name;
	char * seq;
};

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

struct chr get_gene(char * s)
{
	struct chr gene;
	gene.seq="not";
	char * pch;
	pch = strtok(s,"\n");
	int c = 0;
	while(pch !=NULL)
	{
		if(pch[0]=='>')
		{
			gene.chr_name=pch;
			pch = strtok(NULL,"\n");
		}
		
		
		char * seq = gene.seq;
		gene.seq = malloc((strlen(seq) + strlen(pch)) * sizeof(char));
		strcpy(gene.seq,seq);
		strncat(gene.seq,pch,strlen(pch));
		
		
		pch = strtok(NULL,"\n");
		if(strcmp(pch,">chrII")==0)
			break;
	}

	return gene;
}


int main()
{
	char * w303 = read_file();
	struct chr test = get_gene(w303);
	printf("Name: %s\nSequence: %s\n",test.chr_name,test.seq);
	

}