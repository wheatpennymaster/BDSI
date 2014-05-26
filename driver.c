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
	char * pch;
	pch = strtok(s,"\n");
	int c = 0;
	while(pch !=NULL)
	{
		printf("%i: %s\n",c++,pch);
		if(pch[0]=='>')
		{
			gene.chr_name=pch;
			pch = strtok(NULL,"\n");
		}
		
		/*
		char * seq = gene.seq;
		gene.seq = malloc((strlen(seq) + strlen(pch)) * sizeof(char));
		int i;
		for(i=0;i<strlen(seq);i++)
			gene.seq[i]=seq[i];
		for(int j=0;j<strlen(pch);j++,i++)
			gene.seq[i]=pch[j];
		gene.seq[i]='\0';
		
		*/
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
	
	/*
	printf("%lu\n",strlen(w303));
	
	struct chr test[10];
	test[0].chr_name="myname";
	test[0].seq="AA";
	

	for(int t=0;t<5;t++)
	{
		char * seq = test[0].seq;
		char * seq1 = "TT";
	printf("Name: %s\nSequence: %s\n",test[0].chr_name,test[0].seq);
	test[0].seq = malloc((strlen(seq) + strlen(seq1)) * sizeof(char));
	int i;
	for(i=0;i<strlen(seq);i++)
		test[0].seq[i]=seq[i];
	for(int j=0;j<strlen(seq1);j++,i++)
		test[0].seq[i]=seq1[j];
	test[0].seq[i]='\0';
	}
	*/
}