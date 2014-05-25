#include <stdio.h>

struct chr
{
	char * chr_name;
	char * seq;
};

void read_fasta(void)
{
	FILE *fp;
	char line[80];
	char * string;
	fp = fopen("w303.fasta","rt");
	
	while(fgets(line, 80, fp) != NULL)
	{
		sscanf(line,"%s",string);
		printf("%s",string);
	}
}

int main()
{
	read_fasta();

}