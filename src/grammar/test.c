int function(int* ptr);

typedef struct str1tests
{
    int a;
    int b[10];
} str1test;

enum testen 
{
    AAA,
    BBB=3,
    CCC
};

int main(int args, char** argv)
{
    int x,y;
    x=10;
    y=1;
    str1test s;
    s.a=x;
    for(int i = 0; i<10; i++)
    {
        s.b[i]=y;
        y+=1;
    }
    struct str1tests s2;
    s2=s;
    int q = s2.a;
    //q should be 10
    q = sizeof(str1test);
    //q should be 44
    q = sizeof 4;
    //q should be 4
    q = AAA;
    //q should be 0
    q = CCC;
    //q should be 4
    int j=0;
    int result=0;
    do
    {
        result *= s.b[j];
        j++;
    } while(j<10);
    int *resultptr = &result;
    int res2 =*resultptr;
    *resultptr = res2 - 3;
    //result should be 2*3*4*5*6*7*8*9*10*11 - 3
    //s.a should be 10
    int b=function(resultptr);
    //result should be 3
    //b should be 6
    int c = result==4;
    //c should be 0
    int d=!c;
    //d should be 1
    if(c)
    {
        result=5;
    }
    else if(d)
    {
        result = 11;
    }
    else
    {
        result = 17;
    }
    //result should be 11;
    switch(result)
    {
    case 4: result=23; break;
    case 11: result = 24; break;
    }
    //result should be 24
    switch(result)
    {
    case 3: result=3;
    default: result = 5;
    }
    //result should be 5
    result = 1;
    int c = 1;
    while(true)
    {
        if(result++==1)
            continue;
        c++;
        if(result==2)
            break;
    }
    //result should be 2
    //c should be 2
    int d = ((result/c)*36+11-3)%5;
    //d should be 4
    double e = (1.0/2.0)*3+2-4;
    int ok = e<-0.25;
    //ok should be 1
    ok = e>10;
    //ok should be 0
    ok = 3>4;
    //ok should be 0
    ok = 4>=4;
    //ok should be 1
    ok = 4>4;
    //ok should be 0
    ok = 4<4;
    //ok should be 0
    ok = 4<=4;
    //ok should be 1
    ok = 4||0;
    //ok should be 1
    ok = 4&&0;
    //ok should be 0
    ok = 4,5,6;
    //ok should be 6
}

int function(int* ptr)
{
    int x = 1;
    *ptr = ((x<<3>>2)^1);
    int ret = (*ptr | 4) & ~6;
    return ret;
}
